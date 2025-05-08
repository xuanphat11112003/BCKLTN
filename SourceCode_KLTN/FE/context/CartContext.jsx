"use client"; 
import React, { createContext, useReducer, useContext, useEffect } from 'react';
import { useAuth } from './AuthContext';
import cookie from 'react-cookies';

const initialState = { cartItems: 0 };

const MycartContext = createContext();

const MycartReducer = (state, action) => {
    switch (action.type) {
        case 'update':
            return { ...state, cartItems: action.payload };
        default:
            return state;
    }
};


export const MycartProvider = ({ children }) => {
    const [cartCounter, setDispatch] = useReducer(MycartReducer, initialState);

useEffect(() => {
    let cart = cookie.load('cart');
    if (cart) {
        let total = 0;
        for (let c of Object.values(cart)) {
            total += c.quantity;
        }
        setDispatch({
            type: "update",
            payload: total
        });
    } else {
        setDispatch({
            type: "update",
            payload: 0 
        });
    }
}, []);

    
    return (
        <MycartContext.Provider value={{ cartCounter, setDispatch }}>
            {children}
        </MycartContext.Provider>
    );
};


export const useMyCart = () => useContext(MycartContext);
