// middleware.ts
import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';


export function middleware(req: NextRequest) {
    const { pathname } = req.nextUrl;
    const token = req.cookies.get('access-token');
    let user = {};
    try {
        const userCookie = req.cookies.get('user'); 
        const userCookieValue = userCookie ? userCookie.value : ''; 
        user = userCookieValue ? JSON.parse(userCookieValue) : {};
      } catch (error) {
        console.error('Error parsing user cookie:', error);
      }

      console.log(user.role);

  if (!token) {
    if (pathname.startsWith('/auth')) {
      return NextResponse.next();
    }
    return NextResponse.redirect(new URL('/login', req.url)); 
  }
  if (!user || !user.role) {
    return NextResponse.redirect(new URL('/login', req.url)); 
  }

  if (user.role === 'ROLE_AGENCY' && pathname.startsWith('/employee')) {
    return NextResponse.redirect(new URL('/agency', req.url)); 
  }

  if (user.role === 'ROLE_EMPLOYEE' && pathname.startsWith('/agency')) {
    return NextResponse.redirect(new URL('/employee', req.url)); 
  }

  return NextResponse.next(); 
}


export const config = {
  matcher: ['/agency/:path*', '/employee/:path*', '/auth/login', '/auth/register'], };
