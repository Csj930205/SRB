import React, {useState} from 'react';
import {Button, Table} from "react-bootstrap";
import axios from "axios";

function SearchBar() {
    const [query, setQuery] = useState('');
    const [keyword, setKeyword] = useState('');
    const [list, setList] = useState([]);
    const [token, setToken] = useState('');
    const tokenUrl = '/api/spotify/token';
    const handleQuery = (e) => {setQuery(e.target.value)}
    const handleSearch = async () => {
        const {data} = await axios.get(tokenUrl);
        setToken(data.token);

        const response = await axios.get(`https://api.spotify.com/v1/search?q=${query}&type=track&market=KR`, {
            headers : {
                Authorization: `Bearer ${token}`,
            },});
        setList(response.data.tracks.items);
        setKeyword(query)
    };

    return (
        <div>
            <input type="text" value={query} onChange={handleQuery}/>
            <Button variant="outline-primary" type="button" onClick={handleSearch}> 검색 </Button>
            { keyword && <p> {keyword} 에 대한 검색 결과 입니다.</p> }
            <Table striped bordered hover variant="dark">
                <thead>
                <tr>
                    <th>제목</th>
                    <th>찜</th>
                </tr>
                </thead>
                <tbody>
                {list.map(track => (
                    <tr key={track.id}>
                        <td>{track.name}</td>
                        <td></td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    );
}

export default SearchBar;