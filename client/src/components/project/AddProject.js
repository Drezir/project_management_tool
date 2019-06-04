import React, { Component } from 'react'

export default class AddProject extends Component {

    constructor(props) {
        super(props);

        this.state = {
            projectName: "",
            projectIdentifier: "",
            description: "",
            startDate: "",
            endDate: ""
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({[e.target.name]:e.target.value});
    }

    onSubmit(e) {
        e.preventDefault(); // prevent form from reloading
        const newProject = {
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            startDate: this.state.startDate,
            endDate: this.state.endDate
        };
    }

    render() { 
        return (
            <div className="project">
        <div className="container">
            <div className="row">
                <div className="col-md-8 m-auto">
                    <h5 className="display-4 text-center">Create Project form</h5>
                    <hr />
                    <form onSubmit={this.onSubmit}>
                        <div className="form-group">
                            <input 
                            name="projectName" 
                            type="text"
                            className="form-control form-control-lg" 
                            placeholder="Project Name" 
                            value={this.state.projectName}
                            onChange={this.onChange}/>
                        </div>
                        <div className="form-group">
                            <input 
                            name="projectIdentifier" 
                            type="text" 
                            className="form-control form-control-lg" 
                            placeholder="Unique Project ID"
                            disabled
                            value={this.state.projectIdentifier}
                            onChange={this.onChange}/>
                        </div>
                        <div className="form-group">
                            <textarea 
                            name="description" 
                            className="form-control form-control-lg" 
                            placeholder="Project Description"
                            value={this.state.description}
                            onChange={this.onChange}>
                            </textarea>
                        </div>
                        <h6>Start Date</h6>
                        <div className="form-group">
                            <input 
                            name="startDate" 
                            type="date" 
                            className="form-control form-control-lg"
                            value={this.state.startDate}
                            onChange={this.onChange}/>
                        </div>
                        <h6>Estimated End Date</h6>
                        <div className="form-group">
                            <input 
                            type="date" 
                            className="form-control form-control-lg" 
                            name="endDate"
                            value={this.state.endDate}
                            onChange={this.onChange}/>
                        </div>

                        <input type="submit" className="btn btn-primary btn-block mt-4" />
                    </form>
                </div>
            </div>
        </div>
    </div>
        )
    }
}
