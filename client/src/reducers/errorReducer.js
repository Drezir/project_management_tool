import {GET_ERRORS} from '../actions/Types';

const initialState = {
    errors: {},
    stacktrace: ""
};

export default function(state=initialState, action) {
    switch(action.type) {
        case GET_ERRORS: return {
            ...state,
            errors: action.payload.errorItems,
            stacktrace: action.payload.stacktrace
        };
        default: return state;
    }
}