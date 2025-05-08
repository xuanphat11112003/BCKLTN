import { GetServerSideProps } from "next";
import OrderBillForm from "./component/orderBillForm";
import context from "react-bootstrap/esm/AccordionContext";
import { authAPIs } from "@/utils/apis";

const OrderBillPage: React.FC<{orderBill : any}> = ({orderBill}) => {
    return (
        <OrderBillForm orderBill = {orderBill}/>
    );
};


export const getServerSideProps : GetServerSideProps = async (context) => {
    const { req } = context;
    const cookies = req.cookies;
    const token = cookies["access-token"] || null; 
    let orderBill = {};
    try {
        const res = await authAPIs(token).get('/orderOfWareHouse');
        orderBill = res.data;
        return {
            props: {
                orderBill,
            },
        };
    } catch (error) {
        console.error(error);
        return {
            props: {
                orderBill: [],
            },
        };
    }
}
export default OrderBillPage;