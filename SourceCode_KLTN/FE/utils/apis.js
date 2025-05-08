// src/utils/api.js

import axios from "axios";
import cookie from "react-cookies"

const BASE_URL = 'http://localhost:8080/chuoicungung/api/';

export const endpoints = {
    material: '/material',
    login: '/auth/login',
    currentUser: '/users/me',
    supplier: '/supplier',
    order: '/orders',
    warehouse: '/warehouse',
    manufacture: '/orderManu',
    product: '/products',
    materialstock:'/materialStock',
    productstock: '/ProductStock',
    cost:'/Cost',
    vnPay:'/payment/create_payment',
    exportorder: '/orderExport'
};


const authAPIs = (token) => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': token || '',
        },
    });
};


const api = axios.create({
    baseURL: BASE_URL,
});

export { authAPIs, api };
