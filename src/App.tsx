import { BrowserRouter, Routes,Route } from "react-router-dom";
import Login from "./components/Login/Login";
import Home from "./components/home/Home";
import Entrenamiento from "./components/Reconocimiento/Entrenamiento";
import ListarMatriculados from "./components/Asistencia/ListarMatriculados";
import ListarMateriasProfesor from "./components/Asistencia/ListarMateriasProfesor";

//https://react-hot-toast.com/ --> para el toast
//https://www.youtube.com/watch?v=dGYs8I9XY98

function App() {
  
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/matriculados/:idmateriasporpersona" element={<ListarMatriculados />} />
        <Route path="/materiasprofesor" element={<ListarMateriasProfesor />} />
        <Route path="/reconocimiento" element={<Entrenamiento />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App
