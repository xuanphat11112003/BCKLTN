"use client";
import React, { useEffect, useState } from 'react';
import { useMyCart } from '@/context/CartContext.jsx';
import styles from '@/pages/agency/component/index.module.css';
import cookie from "react-cookies";
import { FaClipboardList, FaArrowLeft, FaArrowRight, FaSearch } from 'react-icons/fa';
import { api, endpoints } from '@/utils/apis';
import { Spinner } from 'react-bootstrap';
import ChatBot from '@/components/layout/ChatbotForEmployee';

interface Product {
  id: number;
  name: string;
  price: number;
  image: string;
}

interface Category {
  id: number;
  name: string;
}

interface HomeForCustomerProps {
  initialProducts: Product[];
}

const HomeForCustomer: React.FC<HomeForCustomerProps> = ({ initialProducts }) => {
  const { setDispatch } = useMyCart() || { dispatch: () => { } };
  const [currentIndex, setCurrentIndex] = useState(0);
  const [products, setProducts] = useState<Product[]>(initialProducts);
  const [page, setPage] = useState(0);
  const [hasmore, setHasmore] = useState(true);
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<number | null>(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const res = await api.get('/category');
        setCategories(res.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };

    fetchCategories();
  }, []);

  const order = (p: Product) => {
    let cart = cookie.load('cart') || null;
    if (cart === null) {
      cart = {};
    }
    if (p.id in cart) {
      cart[p.id]['quantity']++;
    } else {
      cart[p.id] = {
        id: p.id,
        name: p.name,
        price: p.price,
        quantity: 1,
        img: p.image,
      };
    }
    cookie.save('cart', cart, { path: '/' });
    console.log("Cart saved to cookie:", cart);

    if (cart !== null) {
      let totalQuantity = 0;
      for (let c of Object.values(cart)) totalQuantity += (c as any).quantity;

      setDispatch({
        type: 'update',
        payload: totalQuantity,
      });
    }
  };

  const nextSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % Math.ceil(products.length / 2));
  };

  const prevSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + Math.ceil(products.length / 2)) % Math.ceil(products.length / 2));
  };

  const loadProduct = async () => {
    if (page > 0) {
      setLoading(true);
      try {
        let url = `${endpoints.product}?page=${page}&category=${selectedCategory || ''}&search=${searchTerm}`;
        let res = await api.get(url);
        console.log(page);
        console.log("API Response:", res.data);
        if (Array.isArray(res.data) && res.data.length > 0) {
          setProducts((prevProducts) => [...prevProducts, ...res.data]);
        } else {
          setHasmore(false);
        }
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    }
  };

  useEffect(() => {
    loadProduct();
  }, [page, selectedCategory, searchTerm]);

  useEffect(() => {
    if (hasmore === true)
      window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [page, hasmore]);

  const handleScroll = () => {
    const lastElement = document.querySelector('.last-element-class');
    const windowHeight = window.innerHeight;
    const body = document.body;
    const html = document.documentElement;
    const documentHeight = Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);
    if (window.scrollY + windowHeight >= documentHeight - 100) {
      setPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    if (products && products.length > 0) {
      const interval = setInterval(nextSlide, 5000);
      return () => clearInterval(interval);
    }
  }, [products?.length]);

  const handleSearch = () => {
    setPage(1); 
    setProducts([]);
  };

  return (
    <>
      <div className="container mx-auto p-4">
        <ChatBot/>
        <div className="flex mb-4">
          <select
            className="border border-gray-300 rounded-md p-2 mr-2"
            value={selectedCategory || ''}
            onChange={(e) => setSelectedCategory(Number(e.target.value))}
          >
            <option value="">Select a category</option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
          <div className="flex items-center">
            <input
              type="text"
              className="border border-gray-300 rounded-md p-2 mr-2"
              placeholder="Search..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button
              onClick={handleSearch}
              className="bg-blue-500 text-white p-2 rounded-md flex items-center"
            >
              <FaSearch className="mr-1" />
              Search
            </button>
          </div>
        </div>

        <div id="productCarousel" className="relative mb-8">
          <div className="relative overflow-hidden">
            <div className="flex transition-transform duration-700" style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
              {Array.isArray(products) && products.length > 0 ? (
                products.reduce((acc, product, index) => {
                  if (index % 2 === 0) {
                    acc.push(
                      <div key={index} className="min-w-full flex">
                        <div className={`${styles['carousel-slide']} w-1/2 p-2 relative`}>
                          <img
                            src={product.image}
                            className="w-full h-64 object-cover rounded-lg"
                            alt={product.name}
                          />
                          <div className="absolute bottom-0 left-0 bg-black bg-opacity-50 text-white p-2">
                            <p className="text-lg font-semibold">${product.price.toFixed(2)}</p>
                          </div>
                        </div>
                        {products[index + 1] && (
                          <div className={`${styles['carousel-slide']} w-1/2 p-2 relative`}>
                            <img
                              src={products[index + 1].image}
                              className="w-full h-64 object-cover rounded-lg"
                              alt={products[index + 1].name}
                            />
                            <div className="absolute bottom-0 left-0 bg-black bg-opacity-50 text-white p-2">
                              <p className="text-lg font-semibold">${products[index + 1].price.toFixed(2)}</p>
                            </div>
                          </div>
                        )}
                      </div>
                    );
                  }
                  return acc;
                }, [])
              ) : (
                <p>No products available.</p>
              )}
            </div>
          </div>
          <button
            onClick={prevSlide}
            className={`${styles['carousel-button']} absolute top-1/2 -left-6 transform -translate-y-1/2`}
          >
            <FaArrowLeft size={24} />
          </button>
          <button
            onClick={nextSlide}
            className={`${styles['carousel-button']} absolute top-1/2 -right-6 transform -translate-y-1/2`}
          >
            <FaArrowRight size={24} />
          </button>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {Array.isArray(products) && products.length > 0 ? (
            products.map((product) => (
              <div key={product.id} className={`${styles['custom-height']} bg-white rounded-lg shadow-lg overflow-hidden`}>
                <img
                  src={product.image}
                  className={`${styles['custom-height2']} w-full h-48 object-cover rounded-t-lg`}
                  alt={product.name}
                />
                <div className="p-4">
                  <h5 className="text-xl font-semibold mb-2">{product.name}</h5>
                  <p className="text-gray-700 mb-2">${product.price.toFixed(2)}</p>
                  <button
                    onClick={() => order(product)}
                    className="bg-blue-500 text-white py-2 px-4 rounded-md"
                  >
                    Add to Cart
                  </button>
                </div>
              </div>
            ))
          ) : (
            <Spinner animation="border" />
          )}
        </div>
      </div>
    </>
  );
};

export default HomeForCustomer;
