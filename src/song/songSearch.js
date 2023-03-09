import React, {useState} from 'react';
import {Button, Table} from "react-bootstrap";
import axios from "axios";

function SongSearch() {
    const [keyword, setKeyword] = useState('');
    const [search, setSearch] = useState('');
    const [songList, setSongList] = useState([]);
    const handleSearch = (e) => {setSearch(e.target.value)};

    const searchSong = async () => {
        const url = `/api/song/list?search=${search}`
        await axios.get(url)
            .then(function (res) {
                if (res.data.code === 200) {
                    setSongList(res.data.songList);
                }
            })
            .catch(error => console.log(error));
        setKeyword(search);
    }


    return (
        <div>
            <input type="text" value={search} onChange={handleSearch}/>{' '}
            <Button variant="outline-primary" type="button" onClick={searchSong}> 검색 </Button>
            { keyword && <p> {keyword} 에 대한 검색 결과 입니다.</p> }
            <Table striped bordered hover variant="dark">
                <thead>
                <tr>
                    <th>가수</th>
                    <th>내용</th>
                    <th>등록시간</th>
                </tr>
                </thead>
                <tbody>
                {songList.map(song => (
                    <tr key={song.seq}>
                        <td>{song.title}</td>
                        <td>{song.content}</td>
                        <td>{song.createdDate}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    );
}

export default SongSearch;