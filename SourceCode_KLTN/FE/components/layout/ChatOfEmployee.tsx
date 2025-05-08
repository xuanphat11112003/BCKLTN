import { useState, useEffect } from "react";
import { initializeApp, getApps } from "firebase/app";
import { getDatabase, ref, onValue, update, set, remove } from "firebase/database";
import { useAuth } from "@/context/AuthContext";

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

const app = getApps().length === 0 ? initializeApp(firebaseConfig) : getApps()[0];
const db = getDatabase(app);

export default function EmployeeChat() {
  const { state } = useAuth();
  const employee = state?.user;
  const [requests, setRequests] = useState([]);
  const [activeChat, setActiveChat] = useState(null);
  const [messages, setMessages] = useState([]);
  const [messageInput, setMessageInput] = useState("");

  useEffect(() => {
    const requestsRef = ref(db, "supportRequests");

    onValue(requestsRef, (snapshot) => {
      const data = snapshot.val();
      if (data) {
        const filteredRequests = Object.entries(data)
          .map(([id, value]) => ({ id, ...value }))
          .filter(req => req.status === "pending" || req.assignedTo === employee?.username);

        setRequests(filteredRequests);
      }
    });
  }, [employee]);

  useEffect(() => {
    console.log("Active chat changed:", activeChat);
    if (!activeChat) return;

    const messagesRef = ref(db, `messages/${activeChat}`);
    onValue(messagesRef, (snapshot) => {
      const data = snapshot.val();
      setMessages(data ? Object.values(data) : []);
    });
  }, [activeChat]);

  const acceptRequest = (id) => {
    update(ref(db, `supportRequests/${id}`), {
        status: "accepted",
        assignedTo: employee.username,
    }).then(() => {
        setActiveChat(id); 
    });
};

  const sendMessage = () => {
    if (!messageInput.trim() || !activeChat) return;

    const messageData = {
      sender: employee.username,
      text: messageInput,
      timestamp: Date.now(),
    };

    set(ref(db, `messages/${activeChat}/${Date.now()}`), messageData);
    setMessageInput("");
  };

  const endSupport = () => {
    if (!activeChat) return;

    remove(ref(db, `supportRequests/${activeChat}`));
    remove(ref(db, `messages/${activeChat}`));
    setActiveChat(null);
  };

  return (
    <div className="p-4">
      <h2>Danh sách yêu cầu hỗ trợ</h2>
      {requests.length === 0 && <p>Không có yêu cầu nào</p>}

      {requests.map((req) => (
        <div key={req.id} className="p-2 border m-2">
          <p>Khách hàng: {req.customerId}</p>
          <p>Trạng thái: {req.status}</p>
          {req.status === "pending" && (
            <button onClick={() => acceptRequest(req.id)} className="bg-green-500 p-2 text-white rounded">
              Chấp nhận
            </button>
          )}
          {req.status === "accepted" && req.assignedTo === employee.username && (
            <button onClick={() => setActiveChat(req.id)} className="bg-blue-500 p-2 text-white rounded">
              Xem cuộc trò chuyện
            </button>
          )}
        </div>
      ))}

      {activeChat && (
        <div className="fixed bottom-20 right-5 bg-white p-4 shadow-lg rounded-md w-80 z-[9999]">
          <button onClick={() => setActiveChat(null)} className="text-red-500 ml-[90%] text-lg font-bold hover:bg-red-100 p-1 rounded">-</button>
          <h3 className="text-center font-bold">Chat hỗ trợ</h3>
          <div className="h-60 overflow-y-auto border p-2">
            {messages.map((msg, index) => (
              <div key={index} className={`p-2 ${msg.sender === employee.username ? "text-right text-blue-500" : "text-left text-gray-700"}`}>
                <p>{msg.text}</p>
                <small>{new Date(msg.timestamp).toLocaleTimeString()}</small>
              </div>
            ))}
          </div>
          <input
            type="text"
            value={messageInput}
            onChange={(e) => setMessageInput(e.target.value)}
            placeholder="Nhập tin nhắn..."
            className="w-full p-2 border rounded"
          />
          <button onClick={sendMessage} className="w-full mt-2 p-2 bg-green-500 text-white rounded">
            Gửi
          </button>
          <button onClick={endSupport} className="w-full mt-2 p-2 bg-red-500 text-white rounded">
            Kết thúc hỗ trợ
          </button>
        </div>
      )}
    </div>
  );
}
