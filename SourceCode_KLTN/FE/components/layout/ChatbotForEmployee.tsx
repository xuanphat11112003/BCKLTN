import { useState, useEffect, useRef } from "react";
import { initializeApp } from "firebase/app";
import { getDatabase, ref, set, onValue, update, push } from "firebase/database";
import { useAuth } from "@/context/AuthContext";
import translate from "libretranslate";
import axios from "axios";

const firebaseConfig = {
  apiKey: "AIzaSyAhFrrnXPbQixBQrXVcKEAMD6sMZVK47mM",
  authDomain: "disasterandcharityapp.firebaseapp.com",
  databaseURL: "https://disasterandcharityapp-default-rtdb.firebaseio.com",
  projectId: "disasterandcharityapp",
  storageBucket: "disasterandcharityapp.firebasestorage.app",
  messagingSenderId: "940334375534",
  appId: "1:940334375534:web:21167a9470b3406ae1fc7f",
  measurementId: "G-72L8KKMZSW"
};

const app = initializeApp(firebaseConfig);
const db = getDatabase(app);

export default function Chatbot() {
  const { state } = useAuth(); 
  const user = state?.user;
  const [chatOpen, setChatOpen] = useState(false);
  const [requestId, setRequestId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [messageInput, setMessageInput] = useState("");
  const [assignedTo, setAssignedTo] = useState(null);
  const [supportMode, setSupportMode] = useState(false);
  const [showSupportForm, setShowSupportForm] = useState(false);
  const [connectingToSupport, setConnectingToSupport] = useState(false);


  const messagesEndRef = useRef(null);

  const translateText = async (text, lc, bc) => {
    try {
        const response = await axios.get("https://translate.googleapis.com/translate_a/single", {
            params: {
                client: "gtx",
                sl: lc,
                tl: bc,
                dt: "t",
                q: text,
            },
        });

        return response.data[0][0][0]; // Lấy kết quả dịch
    } catch (error) {
        console.error("Lỗi dịch ngôn ngữ:", error);
        return text;
    }
};



  useEffect(() => {
    if (!requestId) return;
    const messagesRef = ref(db, `messages/${requestId}`);
    onValue(messagesRef, (snapshot) => {
      const data = snapshot.val();
      if (data) {
        setMessages(Object.values(data));
      }
      if (connectingToSupport) {
        setConnectingToSupport(false);
      }
    });

    const requestRef = ref(db, `supportRequests/${requestId}`);
    onValue(requestRef, (snapshot) => {
      const data = snapshot.val();
      if (data?.assignedTo) {
        setAssignedTo(data.assignedTo); 
        setSupportMode(true);
      }
    });
  }, [requestId]);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const sendMessageToAI = async (message) => {
    if (supportMode) return message;

    try {
        console.log("Tin nhắn gốc:", message);

        const translatedMessage = await translateText(message, "vi", "en");
        console.log("Tin nhắn đã dịch (VI -> EN):", translatedMessage);

        const response = await axios.post("http://localhost:5006/webhooks/rest/webhook", {
            sender: user?.username || "guest",
            message: translatedMessage,
        }, {
            headers: { "Content-Type": "application/json" },
            withCredentials: false,
        });

        console.log("Phản hồi từ AI:", response.data);

        if (response.data?.length > 0 && response.data[0]?.text) {
            const finalResponse = await translateText(response.data[0].text, "en", "vi");
            console.log("Phản hồi đã dịch (EN -> VI):", finalResponse);
            return finalResponse;
        }

        return "Xin lỗi, tôi không hiểu câu hỏi của bạn.";
    } catch (error) {
        console.error("Lỗi AI:", error.response?.data || error.message);
        return "Xin lỗi, hiện tại tôi không thể trả lời câu hỏi này.";
    }
};


  const sendUserMessage = async () => {
    if (!messageInput.trim() || !user) return;

    const userMessage = { sender: user.username, text: messageInput, timestamp: Date.now() };
    setMessages([...messages, userMessage]);
    setMessageInput("");
    const aiResponse = await sendMessageToAI(messageInput);
    const aiMessage = { sender: supportMode ? "Nhân viên hỗ trợ" : "Chatbot", text: aiResponse, timestamp: Date.now() };
    setMessages([...messages, userMessage, aiMessage]);

    if (messageInput.toLowerCase().includes("liên hệ nhân viên kỹ thuật")) {
      setConnectingToSupport(true);
      setMessages(prevMessages => [...prevMessages, { sender: "Chatbot", text: "🔄 Đang kết nối với nhân viên kỹ thuật...", timestamp: Date.now() }]);
      requestSupport();
      return;
    }

    if (!supportMode && aiResponse.includes("Xin lỗi, tôi không hiểu câu hỏi của bạn.")) {
      setShowSupportForm(true);
    }
  };

  const handleChatOpen = () => {
    setChatOpen(true);
    if (messages.length === 0) {
      const welcomeMessage = { sender: "Chatbot", text: "🤖 Xin chào! Đây là tin nhắn tự động từ AI. Nếu bạn cần hỗ trợ từ nhân viên, hãy nhập 'liên hệ nhân viên kỹ thuật'.", timestamp: Date.now() };
      setMessages([welcomeMessage]);
    }
  };

  const sendMessageToEmployee = async (message) => {
    if (!supportMode || !requestId) return;

    const messagesRef = ref(db, `messages/${requestId}`);
    const employeeMessage = { sender: user?.username, text: message, timestamp: Date.now() };

    setMessages(prevMessages => [...prevMessages, employeeMessage]);
    setMessageInput("");
    const newMessageRef = push(messagesRef);
    await set(newMessageRef, employeeMessage);
  };

  const requestSupport = async () => {
    if (!user) return;

    const requestRef = ref(db, "supportRequests");
    const newRequestRef = push(requestRef);

    const requestData = {
      customerId: user.username,
      status: "pending",
      assignedTo: null,
    };

    await set(newRequestRef, requestData);
    setRequestId(newRequestRef.key);
    setSupportMode(true);
    setShowSupportForm(false);
  };

  const cancelSupportRequest = () => {
    const confirmCancel = window.confirm("Bạn có chắc chắn muốn hủy hỗ trợ không?");
    if (confirmCancel) {
      const requestRef = ref(db, `supportRequests/${requestId}`);
      update(requestRef, { status: "canceled" });

      setRequestId(null);
      setChatOpen(false);
      setAssignedTo(null);
      setSupportMode(false);
      setMessages([]);
    }
  };

  return (
    <div>
      <button onClick={handleChatOpen} className="fixed bottom-5 right-5 p-3 bg-blue-500 text-white rounded-full">
        💬 Chatbot
      </button>

      {chatOpen && (
        <div className="fixed bottom-20 right-5 bg-white p-4 shadow-lg rounded-md w-80 z-[9999]">
          <button onClick={cancelSupportRequest} className="text-red-500 ml-[90%] text-lg font-bold hover:bg-red-100 p-1 rounded">❌</button>
          <h3 className="text-center font-bold">Hỗ trợ khách hàng</h3>

          <div className="h-60 overflow-y-auto border p-2">
            {messages.map((msg, index) => (
              <div key={index} className={`p-2 ${msg.sender === user.username ? "text-right text-blue-500" : "text-left text-gray-700"}`}>
                <p>{msg.text}</p>
                <small>{new Date(msg.timestamp).toLocaleTimeString()}</small>
              </div>
            ))}
            <div ref={messagesEndRef} />
          </div>

          {showSupportForm && (
            <div className="mt-3">
              <p className="text-center text-red-500">Chatbot không thể trả lời, bạn có muốn liên hệ nhân viên không?</p>
              <button onClick={requestSupport} className="w-full p-2 bg-green-500 text-white rounded mt-2">Chấp nhận</button>
              <button onClick={() => setShowSupportForm(false)} className="w-full p-2 bg-red-500 text-white rounded mt-2">Từ chối</button>
            </div>
          )}

          <input type="text" value={messageInput} onChange={(e) => setMessageInput(e.target.value)} className="w-full p-2 border rounded" />
          <button onClick={supportMode ? () => sendMessageToEmployee(messageInput) : sendUserMessage} className="w-full mt-2 p-2 bg-green-500 text-white rounded">
            Gửi
          </button>
        </div>
      )}
    </div>
  );
}
