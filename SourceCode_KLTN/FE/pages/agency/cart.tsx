import { lazy, Suspense } from "react";
import LoadingOverlay from "./component/loadingOverLoad";

const CartShop = lazy(() => import("./component/cartShop"));
const Cart : React.FC = () =>{
    return(
        <Suspense fallback = {<LoadingOverlay/>}>
            <CartShop/>
        </Suspense>
    );
}
export default Cart;