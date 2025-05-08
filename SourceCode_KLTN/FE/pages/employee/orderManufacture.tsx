import { GetServerSideProps } from "next";
import context from "react-bootstrap/esm/AccordionContext";
import OrderManufactureForm, { OrdersProps } from "./component/oderManufactureForm";
import { authAPIs, endpoints } from "@/utils/apis";

const OrderManufacture : React.FC<{ initialOrders: OrdersProps[] }> = ({initialOrders}) => {
    return(
        <OrderManufactureForm initialOrders= {initialOrders}/>
    );
};

export const getServerSideProps: GetServerSideProps = async (context)=>{
    const { req } = context;
    const cookies = req.cookies;
    const token = cookies["access-token"] || null; 
    let initialOrders = [];
    try {
        const res = await authAPIs(token).get(endpoints.manufacture);
        initialOrders = res.data;
        return {
            props: {
                initialOrders,
            },
        };
    } catch (error) {
        console.error(error);
        return {
            props: {
                initialOrders: [],
            },
        };
    }
}

export default OrderManufacture;