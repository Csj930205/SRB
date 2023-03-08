import {BrowserRouter, Routes, Route} from "react-router-dom";
import Home from "./home";
import Article from "./article/article";
import InsertArticle from "./article/insertArticle";
import Header from "./header";
import Signup from "./login/signup";
import Login from "./login/login";
import SongList from "./song/songList";
import './App.css';

function App() {
  return (
            <BrowserRouter>
                <Header/>
                    <Routes>
                        <Route path='/' element={<Home />} />
                        <Route path='/signup' element={<Signup />} />
                        <Route path='/login' element={<Login />} />
                        <Route path='/article' element={<Article />} />
                        <Route path='/insertArticle' element={<InsertArticle />} />
                        <Route path='/songList' element={<SongList />} />
                    </Routes>

            </BrowserRouter>
  );
}

export default App;
