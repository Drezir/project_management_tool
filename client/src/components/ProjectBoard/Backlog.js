import React, { Component } from 'react'
import ProjectTask from './ProjectTasks/ProjectTask';

export default class Backlog extends Component {
    render() {

        const { projectTasks } = this.props;

        let todo = [];
        let inprogress = [];
        let done = [];
        for (let i = 0; i < projectTasks.length; ++i) {
            const task = projectTasks[i];
            switch(task.status) {
                case "INPROGRESS": inprogress.push(task); break;
                case "DONE": done.push(task); break;
                default: todo.push(task);
            }
        }

        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-secondary text-white">
                                <h3>TO DO</h3>
                            </div>
                        </div>
                        {todo.map(task => <ProjectTask key={task.id} projectTask={task}/>)}
                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-primary text-white">
                                <h3>IN PROGRESS</h3>
                            </div>
                        </div>
                        {inprogress.map(task => <ProjectTask key={task.id} projectTask={task}/>)}
                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-success text-white">
                                <h3>DONE</h3>
                            </div>
                        </div>
                        {done.map(task => <ProjectTask key={task.id} projectTask={task}/>)}
                    </div>
                </div>
            </div>
        )
    }
}
