import {GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK} from '../actions/Types';
import { bindActionCreators } from 'C:/Users/Adam/AppData/Local/Microsoft/TypeScript/3.4.5/node_modules/redux';


const initialState = {
    projectTasks: [],
    projectTask: {}
};

export default function(state=initialState, action) {
    switch(action.type) {
        case GET_BACKLOG: return {
            ...state,
            projectTasks: action.payload
        }
        case GET_PROJECT_TASK: return {
            ...state,
            projectTask: action.payload
        }
        case DELETE_PROJECT_TASK: 
        debugger;
        return {
            ...state,
            projectTasks: state.projectTasks.filter(task => 
                task.projectIdentifier !== action.payload.backlogId 
                && task.projectSequence !== action.payload.projectTaskId)
        }
        default: return state;
    }
}