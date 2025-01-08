import { ReactNode } from "react";
import AccessProtected from "./AccessProtected";
import { KEY } from "../../Messages";
import { Outlet } from "react-router-dom";

interface ProtectedRouteProps
{
    children?:ReactNode,
    redirectTo?:string
}

function ProtectedRoute(props:ProtectedRouteProps)
{

    const {children, redirectTo='/'}=props;
    console.log(redirectTo);
    const permiso=localStorage.getItem("admin")as unknown as boolean;

    if(!localStorage.getItem(KEY)!=null && !permiso )
        return <AccessProtected />

    return children?children:<Outlet/>
}


export default ProtectedRoute;