import { Link } from 'react-router-dom';
import style from './AccessUtils.module.css'

interface SelectDivProps
{
    nameops:string[],
    links:string[],
    eventClick?:Array<(e: React.MouseEvent<HTMLAnchorElement, MouseEvent>)=>void>,
    id?:number
}

function SelectDiv(props :SelectDivProps)
{
    const {nameops,links,eventClick} =props

    return (
        <div className={style.select_container}>
            <div className={style.select_header}>Seleccione una opción</div>
            <div className={style.select_options}>
                {nameops.map((name,index)=>
                   <Link key={index} onClick={eventClick!=null?eventClick[index]:(e)=>e} style={{ textDecoration: 'none', color: 'inherit' }} to={links[index]}><div className={style.select_option}>{name}</div></Link> 
                )}
            </div>
        </div>
    );
}

export default SelectDiv;