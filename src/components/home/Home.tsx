import { ADMIN } from "../../Messages";
import NavBar from "../UtilsComponents/NavBar";

function Home()
{
    const admin=localStorage.getItem(ADMIN)==='true';
    admin;
    return(
        <>
            <NavBar/>
        </>
    );
}

export default Home;