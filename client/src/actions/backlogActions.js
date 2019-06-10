import axios from 'axios';
import {GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK} from './Types';

export const addProjectTask = (backlogId, projectTask, history) => async dispatch => {
    try {
        await axios.post(`/api/backlog/${backlogId}`, projectTask);
        history.push(`/projectBoard/${backlogId}`);
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

export const updateProjectTask = (backlogId, projectTaskId, projectTask, history) => async dispatch => {
    try {
        await axios.put(`/api/backlog/${backlogId}/${projectTaskId}`, projectTask);
        history.push(`/projectBoard/${backlogId}`);
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

export const getProjectTask = (backlogId, projectTaskId) => async dispatch => {
    try {
        const response = await axios.get(`/api/backlog/${backlogId}/${projectTaskId}`);
        dispatch({
            type: GET_PROJECT_TASK,
            payload: response.data
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
};

export const getBacklog = (backlogId) => async dispatch => {
    try {
        const response = await axios.get(`/api/backlog/${backlogId}`);
        dispatch({
            type: GET_BACKLOG,
            payload: response.data
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
};

export const deleteProjectTask = (backlogId, projectTaskId) => async dispatch => {
    try {
        await axios.delete(`/api/backlog/${backlogId}/${projectTaskId}`);
        dispatch({
            type: DELETE_PROJECT_TASK,
            payload: { backlogId, projectTaskId}
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
};

