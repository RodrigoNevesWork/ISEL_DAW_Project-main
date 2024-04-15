/*
// The CSS style for the wrapper div, in order to use CSS Grid.
import * as React from "react";
import {SourceState,TargetState} from "../pages/drag-and-drop";



export const wrapperStyle: React.CSSProperties = {
    gridTemplateColumns: "repeat(3, 1fr)",
    gridAutoRows: "minmax(100px, auto)",
}

export const wrapperStyleBoard: React.CSSProperties = {
    display: 'grid',
    gridTemplateColumns: "repeat(3, 1fr)",
    gap: "10px",
    gridAutoRows: "minmax(100px, auto)",
}

// The function to compute the CSS style for a source `div`
export function sourceDivStyle(sourceState: SourceState, column: number, row: number): React.CSSProperties {
    return {
        gridColumn: column,
        gridRow: row,
        border: "solid",
        width: "50px",
        height: "50px",
        borderColor: sourceState == 'available' ? 'green' : 'red',
    }
}

// The function to compute the CSS style for a target `div`
export function targetDivStyle(targetState: TargetState,column: number, row: number): React.CSSProperties {
    return {
        gridColumn: column,
        gridRow: row,
        border: "solid",
        width: "100px",
        height: "100px",
        borderColor: targetState === undefined ? 'green' : 'red'
    }
}
*/