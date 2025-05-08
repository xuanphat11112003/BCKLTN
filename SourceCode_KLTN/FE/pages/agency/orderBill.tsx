import { authAPIs, endpoints } from "@/utils/apis";
import { GetServerSideProps } from "next";
import OrderBillForm from "./component/orderBillForm";


const OrderBill: React.FC<{ Order: any }> = ({ Order }) => {
    if (!Order || Order.length === 0) {
        return <p>No orders found.</p>;
    }

    return (
        <div className="container mx-auto p-4">
            <OrderBillForm orders={Order} />
        </div>
    );
};

export const getServerSideProps: GetServerSideProps = async (context) => {
    const { req } = context;
    const cookies = req.cookies;
    const token = cookies["access-token"] || null; 
    let Order = [];
    
    try {
        const res = await authAPIs(token).get(endpoints.exportorder);
        Order = res.data;
        return {
            props: {
                Order,
            },
        };
    } catch (error) {
        console.error(error);
        return {
            props: {
                Order: [],
            },
        };
    }
}

export default OrderBill;
