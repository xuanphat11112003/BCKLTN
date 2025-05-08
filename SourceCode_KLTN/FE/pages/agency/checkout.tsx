import context from "react-bootstrap/esm/AccordionContext";
import CheckoutForm from "./component/CheckoutForm";
import cookie from "react-cookies"
import { authAPIs, endpoints } from "@/utils/apis";
import { GetServerSideProps } from "next";

const checkout: React.FC = ({Cost})=> {
    return(
        <CheckoutForm Cost = {Cost}/>
    );
};
export const getServerSideProps : GetServerSideProps = async(context)=>{
    let Cost = {};
    const { req } = context;
    const cookies = req.cookies;
    const user = cookies.user ? JSON.parse(cookies.user) : null;
    const cart = cookies.cart ? JSON.parse(cookies.cart) : null;
    const token = cookies["access-token"] || null; 
    if (!user || !cart) {
        return { props: { Cost: null } };
    }
    const userAddress = user.address;
    const details = Object.values(cart).map((item: any) => ({
        productId: item.id,
        quantity: item.quantity,
    }));
    try{
        
        const response  = await  authAPIs(token).post(endpoints.cost, {
            userAddress,
            details
        });
        Cost = {
            ...response.data,
            cart: cart,
        };
       
        return {
            props: {
                Cost: Cost,
            },
        };

    }catch(error) {
        console.error("Error fetching cost data:", error);
        return {
            props: {
                Cost: null,
            },
        };
    }
};
export default checkout;