import axios from 'axios';
import {SET_CURRENT_USER, GET_ERRORS} from './Types';
import setJWTToken from '../utils/setJWTToken';
import jwt_decode from 'jwt-decode';

export const createNewUser = (newUser, history) => async dispatch => {
    try {
        await axios.post("/api/users/register", newUser);
        history.push('/login');
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
};

export const login = (LoginRequest, history) => async dispatch => {
    try {
        const response = await axios.post("/api/users/login", LoginRequest);
        // extract token
        const {token} = response.data;
        // store token
        localStorage.setItem("jwtToken", token);
        // set token header to axios
        setJWTToken(token);
        // decode token
        const decodedToken = jwt_decode(token);
        dispatch({
            type: SET_CURRENT_USER,
            payload: decodedToken
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
};