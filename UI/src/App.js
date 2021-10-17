import logo from './logo.svg';
import SideRegister from "./components/SideRegister"; 
import { ToastContainer, toast } from "react-toastify";
import './App.css';
import { Button } from '@material-ui/core';

function App() {
  
  return (
    <div className="App">
        <SideRegister/>
    </div>
  );
}

export default App;
