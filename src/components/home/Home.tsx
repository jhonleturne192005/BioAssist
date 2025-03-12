import { ADMIN } from "../../Messages";
import NavBar from "../UtilsComponents/NavBar";
import NavBarAdmin from "../UtilsComponents/NavBarAdmin";


function Home()
{
    const admin=localStorage.getItem(ADMIN)==='true';

    return(
        <>
        {admin?<NavBarAdmin/>:<NavBar/>}
        </>
    );
}

export default Home;