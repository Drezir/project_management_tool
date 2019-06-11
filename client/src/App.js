import React from 'react';
import logo from './logo.svg';
import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/layout/Header';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import AddProject from './components/project/AddProject';
import {Provider} from 'react-redux';
import store from './store';
import UpdateProject from './components/project/UpdateProject';
import ProjectBoard from './components/ProjectBoard/ProjectBoard';
import AddProjectTask from './components/ProjectBoard/ProjectTasks/AddProjectTask';
import UpdateProjectTask from './components/ProjectBoard/ProjectTasks/UpdateProjectTask';
import Landing from './components/layout/Landing';
import Login from './components/userManagement/Login';
import Register from './components/userManagement/Register';
import jwt_decode from 'jwt-decode';
import setJWTToken from './utils/setJWTToken';
import { SET_CURRENT_USER } from './actions/Types';

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decodedToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decodedToken
  });


  const currentTime = Date.now() / 1000;
  if (decodedToken.exp < currentTime) {
    window.location.href = "/";
  }
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          {
            // public routes
          }
          <Route exact path="/" component={Landing}/>
          <Route exact path="/login" component={Login}/>
          <Route exact path="/register" component={Register}/>
          {
            // private routes
          }
          <Route exact path="/dashboard" component={Dashboard}/>
          <Route exact path="/addProject" component={AddProject}/>
          <Route exact path="/updateProject/:id" component={UpdateProject}/>

          <Route exact path="/projectBoard/:id" component={ProjectBoard}/>
          <Route exact path="/addProjectTask/:id" component={AddProjectTask}/>
          <Route exact path="/updateProjectTask/:backlogId/:taskId" component={UpdateProjectTask}/>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
