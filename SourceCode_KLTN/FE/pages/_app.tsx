import FooterForAgency from "@/components/layout/FooterForAgency";
import FooterForEmployee from "@/components/layout/FooterForEmployee";
import HeaderForAgency from "@/components/layout/HeaderForAgency";
import Header from "@/components/layout/HeaderForEmployee";
import Sidebar from "@/components/layout/SideBarForEmployee";
import { AuthProvider, useAuth } from "@/context/AuthContext";
import { MycartProvider } from "@/context/CartContext";
import "@/styles/globals.css";
import type { AppProps } from "next/app";
import { useRouter } from "next/router";
import React from "react";


const Layout: React.FC<{children : React.ReactNode}> = ({children }) => {
    const {state} = useAuth();
    const { user } = state;
    console.log(user);
    const router = useRouter();
    const isLoginPage = router.pathname === '/login';
    return(
      <>
                {!isLoginPage && (user && user.role === "ROLE_AGENCY" ? <HeaderForAgency /> : <Header />)}
            
                {!isLoginPage && user?.role === "ROLE_EMPLOYEE" && <Sidebar />}
                <div className={isLoginPage ? '' : (user?.role === "ROLE_AGENCY" ? '' : 'flex flex-col min-h-screen')}>
                <main className={`${isLoginPage ? '' : (user?.role === "ROLE_AGENCY" ? '' : 'flex-1 p-4 ml-64')}`}>
                    {children}
                </main>
                </div>
                {!isLoginPage && (user && user.role === "ROLE_AGENCY" ? <FooterForAgency /> : <FooterForEmployee />)}
           
        </>
    );
};

export default function App({ Component, pageProps }: AppProps) {
  return(
    <AuthProvider>
      <MycartProvider >
        <Layout>
          <Component {...pageProps} />
        </Layout>
      </MycartProvider>
    </AuthProvider>
  ) ;
}
