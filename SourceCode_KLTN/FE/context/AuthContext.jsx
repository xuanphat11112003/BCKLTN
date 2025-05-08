"use client";
import cookie from "react-cookies"
import React, { createContext, useReducer, useContext, useEffect } from 'react';
const initialState = {
    user:  null, 
};

const AuthContext = createContext();

const authReducer = (state, action) => {
    switch (action.type) {
        case 'LOGIN':
            return { ...state, user: action.payload };
        case 'LOGOUT':
            cookie.remove('user');
            cookie.remove('access-token');
            return { ...state, user: null };
        default:
            return state;
    }
};

export const AuthProvider = ({ children }) => {
    const [state, dispatch] = useReducer(authReducer, initialState);
    useEffect(() => {
        const userCookie = cookie.load('user');
        console.log('User Cookie:', userCookie);
        if (userCookie) {
            dispatch({ type: 'LOGIN', payload: userCookie });
        }
    }, []);

    return (
        <AuthContext.Provider value={{ state, dispatch }}>
            {children}
        </AuthContext.Provider>
    );
};


export const useAuth = () => useContext(AuthContext);

