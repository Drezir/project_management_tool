import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {createProject} from '../../actions/projectActions';
import classnames from 'classnames';
import FieldError from '../FieldError';

class AddProject extends Component {

    constructor(props) {
        super(props);

        this.state = {
            projectName: "",
            projectIdentifier: "",
            description: "",
            startDate: "",
            endDate: "",

            errorObject: {
                errors: {}
            }
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errorObject) {
            this.setState({
                errorObject: nextProps.errorObject
            })
        }
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
        this.props.createProject(newProject, this.props.history);
    }

    render() { 
        const {errors} = this.state.errorObject;
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
                            className={classnames("form-control form-control-lg", {
                                "is-invalid": errors.projectName
                            })}
                            placeholder="Project Name" 
                            value={this.state.projectName}
                            onChange={this.onChange}/>
                            {errors.projectName && (
                                <div className="invalid-feedback"><FieldError errors={errors.projectName}/></div>
                            )}
                        </div>
                        <div className="form-group">
                            <input 
                            name="projectIdentifier" 
                            type="text" 
                            className={classnames("form-control form-control-lg", {
                                "is-invalid": errors.projectIdentifier
                            })}
                            placeholder="Unique Project ID"
                            value={this.state.projectIdentifier}
                            onChange={this.onChange}/>
                            {errors.projectIdentifier && (
                                <div className="invalid-feedback"><FieldError errors={errors.projectIdentifier}/></div>
                            )}
                        </div>
                        <div className="form-group">
                            <textarea 
                            name="description" 
                            className={classnames("form-control form-control-lg", {
                                "is-invalid": errors.description
                            })}
                            placeholder="Project Description"
                            value={this.state.description}
                            onChange={this.onChange}>
                            </textarea>
                            {errors.description && (
                                <div className="invalid-feedback"><FieldError errors={errors.description}/></div>
                            )}
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

AddProject.propTypes = {
    createProject: PropTypes.func.isRequired,
    errorObject: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    errorObject: state.errorObject
});

export default connect(
    mapStateToProps, 
    {createProject}
) (AddProject);