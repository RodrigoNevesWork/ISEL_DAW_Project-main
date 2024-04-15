import React from 'react';

interface BoardProps {
    //playerBoard
    rows: number;
    cols: number;
    onClick: (row: number, col: number) => void;
}


const Board: React.FC<BoardProps> = ({ rows, cols, onClick }) => (
    <table align={"center"}>
        <tbody>
        {[...Array(rows)].map((_, row) => (
            <tr key={row}>
                {[...Array(cols)].map((_, col) => (
                    <td key={col} onClick = {() => onClick(row, col)} style={{ width: 50, height: 50 , backgroundColor: 'lightgrey',
                        border: '1px solid grey',
                        textAlign: 'center',
                        verticalAlign: 'middle'}}>
                    </td>
                ))}
            </tr>
        ))}
        </tbody>
    </table>
);

export default Board;