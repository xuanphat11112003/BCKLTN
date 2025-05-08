
import { GetStaticProps } from "next";
import HomeForCustomer from "./component/indexForAgency";
import { api, endpoints } from "@/utils/apis";


const IndexAgency: React.FC = ({ products }) =>{
    return (
        <HomeForCustomer initialProducts={products}/>
    );
};
export const getStaticProps: GetStaticProps  = async () => {
    let products = [];
    try {
      const res = await api.get(`${endpoints['product']}`);
      products = res.data || [];
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  
    return {
      props: {
        products,
        revalidate: 60,
      },
    };
  };
  export default IndexAgency;