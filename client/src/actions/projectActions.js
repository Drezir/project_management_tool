import axios from 'axios';
import {GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT} from './Types';

export const createProject = (project, history) => async dispatch => {
    try {
        await axios.post("/api/project", project);
        history.push("/dashboard");
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

export const getProjects = () => async dispatch => {
    const response = await axios.get("/api/project/all");
    dispatch({
        type: GET_PROJECTS,
        payload: response.data
    });
};

export const getProject = (id, history) => async dispatch => {
    try {
        const response = await axios.get(`/api/project/${id}`);
        dispatch({
            type: GET_PROJECT,
            payload: response.data
        });
    } catch (error) {
        history.push("/dashboard");
    }
}

export const deleteProject = (id) => async dispatch => {
    await axios.delete(`/api/project/${id}`);
    dispatch({
        type: DELETE_PROJECT,
        payload: id
    });
}