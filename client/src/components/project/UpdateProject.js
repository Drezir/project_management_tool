import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {getProject, createProject} from '../../actions/projectActions';
import classnames from 'classnames';
import ErrorPage from '../ErrorPage';
import FieldError from '../FieldError';

class UpdateProject extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id: "",
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

        const {
            id,
            projectName,
            projectIdentifier,
            description,
            startDate,
            endDate
        } = nextProps.project;

        this.setState({
            id: id,
            projectName: projectName,
            projectIdentifier: projectIdentifier,
            description: description,
            startDate: startDate,
            endDate: endDate,
        })

        if (nextProps.errorObject) {
            this.setState({
                errorObject: nextProps.errorObject
            })
        }
    }

    componentDidMount() {
        const {id} = this.props.match.params;
        this.props.getProject(id, this.props.history);
    }

    onChange(e) {
        this.setState({[e.target.name]:e.target.value});
    }

    onSubmit(e) {
        e.preventDefault(); // prevent form from reloading
        const updatedProject = {
            id: this.state.id,
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            startDate: this.state.startDate,
            endDate: this.state.endDate
        };
        this.props.createProject(updatedProject, this.props.history);
    }

    render() { 
        const {errors} = this.state.errorObject;
        if (this.state.errorObject.stacktrace) {
            return <ErrorPage errorObject={this.state.errorObject}/>
        }
        return (
            <div className="project">
        <div className="container">
            <div className="row">
                <div className="col-md-8 m-auto">
                    <h5 className="display-4 text-center">Update Project form</h5>
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
                            onChange={this.onChange}
                            disabled/>
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

UpdateProject.propTypes = {
    getProject: PropTypes.func.isRequired,
    createProject: PropTypes.func.isRequired,
    project: PropTypes.object.isRequired,
    errorObject: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    project: state.project.project,
    errorObject: state.errorObject
});

export default connect(
    mapStateToProps, 
    {getProject, createProject}
) (UpdateProject);