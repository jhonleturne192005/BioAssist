import { ReactNode } from "react";
import AccessProtected from "./AccessProtected";
import { ADMIN, KEY } from "../../Messages";
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
    const permiso=localStorage.getItem(ADMIN) === "true" ;
    
    
    console.log(!(localStorage.getItem(KEY)===null));
    console.log(!permiso);

    console.log("ese man1"+localStorage.getItem(KEY));
    console.log("ese man2"+localStorage.getItem(ADMIN));


    console.log("que nota" );

    if(!(localStorage.getItem(KEY)===null) && !permiso )
        return <AccessProtected />

    return children?children:<Outlet/>
}


export default ProtectedRoute;