import { useEffect, useState } from "react";
import { URL_BACK } from "./URL_BASE";
import axios, { AxiosRequestConfig } from "axios";


export function UseFetchPOSTEvent(form:FormData,url:string)
{
    const myHeaders: Record<string, string> = {
        'Content-Type': 'multipart/form-data',
    };

    const config:AxiosRequestConfig  = {
        headers:myHeaders,
        withCredentials:true,
    };


    return axios.post(`${URL_BACK}/${url}`,form,config)

}


export function UseFetchGETEvent(params:Record<string, string>,url:string)
{
    const myHeaders: Record<string, string> = {
        'Content-Type': 'application/json',
    };

    const paramsOBJ = params;

    const config:AxiosRequestConfig  = {
        headers:myHeaders,
        withCredentials:true,
        params: paramsOBJ
    };

    return axios.get(`${URL_BACK}/${url}`,config);
}



export function UseFetchPOST(form:FormData,url:string,typeobj:boolean)
{
    const myHeaders: Record<string, string> = {
        'Content-Type': 'multipart/form-data',
    };

    const config:AxiosRequestConfig  = {
        headers:myHeaders,
        withCredentials:true,
    };

    const [data,setData]=useState({data:typeobj?[]:{}})
    const [loading,setLoading]=useState(true);
    const [error,setError]=useState(null);



    useEffect(()=>{
        axios.post(`${URL_BACK}/${url}`,form,config)
        .then((response)=>{
            console.log(response.data);
            setData(response.data);
        })
        .catch((error)=>{
            console.log(error);
            setError(error);
        })
        .finally(()=>{
            console.log("peticion finalizada");
            setLoading(true);
        });
    },[]);
   

    return {data,loading,error}

}

export function UseFetchGET(params:Record<string, string>,url:string,typeobj:boolean)
{
    const myHeaders: Record<string, string> = {
        'Content-Type': 'application/json',
    };

    const paramsOBJ = params;

    const config:AxiosRequestConfig  = {
        headers:myHeaders,
        withCredentials:true,
        params: paramsOBJ
    };

    const [data,setData]=useState({data:typeobj?[]:{}});
   // const [data,setData]=useState({data:[]});
    const [loading,setLoading]=useState(true);
    const [error,setError]=useState({});


    useEffect(()=>
    {

        axios.get(`${URL_BACK}/${url}`,config)
        .then((response)=>{
            //console.log(response.data);
            setData(response.data);
        })
        .catch((error)=>{
            //console.log(error);
            setError(error);
        })
        .finally(()=>{
            //console.log("peticion finalizada");
            setLoading(true);
        });
    },[]);


    return {data,loading,error}
}

