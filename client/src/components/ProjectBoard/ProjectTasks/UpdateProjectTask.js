import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { updateProjectTask, getProjectTask } from '../../../actions/backlogActions';
import PropTypes from 'prop-types';
import FieldError from '../../FieldError';

class UpdateProjectTask extends Component {

    constructor(props) {
        super(props);

        const { id } = props;

        this.state = {
            summary: "",
            acceptanceCriteria: "",
            status: "",
            priority: "0",
            dueDate: "",
            projectIdentifier: id,
            projectSequence: "",

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
            summary,
            acceptanceCriteria,
            status,
            priority,
            dueDate,
            projectIdentifier,
            projectSequence
        } = nextProps.projectTask;

        this.setState({
            id: id,
            summary: summary,
            acceptanceCriteria: acceptanceCriteria,
            status: status,
            priority: priority,
            dueDate: dueDate,
            projectIdentifier: projectIdentifier,
            projectSequence: projectSequence
        })

        if (nextProps.errorObject) {
            this.setState({
                errorObject: nextProps.errorObject
            })
        }
    }

    componentWillMount() {
        const {backlogId, taskId} = this.props.match.params;
        this.props.getProjectTask(backlogId, taskId);
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    onSubmit(e) {
        e.preventDefault(); // prevent form from reloading

        const newProjectTask = {
            id: this.state.id,
            summary: this.state.summary,
            acceptanceCriteria: this.state.acceptanceCriteria,
            status: this.state.status,
            priority: this.state.priority,
            dueDate: this.state.dueDate
        };
        this.props.updateProjectTask(
            this.state.projectIdentifier,
            this.state.projectSequence,
            newProjectTask, 
            this.props.history
        );
    }

    render() {

        const { id } = this.props.match.params;
        const { errors } = this.state.errorObject;

        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${id}`} className="btn btn-light">
                                Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Edit Project Task</h4>
                            <p className="lead text-center">{this.state.projectIdentifier} / {this.state.projectSequence}</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input
                                        type="text"
                                        name="summary"
                                        placeholder="Project Task summary"
                                        className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.summary
                                        })}
                                        value={this.state.summary}
                                        onChange={this.onChange} />
                                    {errors.summary && (
                                        <div className="invalid-feedback"><FieldError errors={errors.summary}/></div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <textarea
                                        className="form-control form-control-lg"
                                        placeholder="Acceptance Criteria"
                                        name="acceptanceCriteria"
                                        value={this.state.acceptanceCriteria}
                                        onChange={this.onChange}>
                                    </textarea>
                                </div>
                                <h6>Due Date</h6>
                                <div className="form-group">
                                    <input
                                        type="date"
                                        className="form-control form-control-lg"
                                        name="dueDate"
                                        value={this.state.dueDate}
                                        onChange={this.onChange} />
                                </div>
                                <div className="form-group">
                                    <select
                                        className="form-control form-control-lg"
                                        name="priority"
                                        value={this.state.priority}
                                        onChange={this.onChange}>
                                        <option value={0}>Select Priority</option>
                                        <option value={1}>High</option>
                                        <option value={2}>Medium</option>
                                        <option value={3}>Low</option>
                                    </select>
                                </div>

                                <div className="form-group">
                                    <select
                                        className="form-control form-control-lg"
                                        name="status"
                                        value={this.state.status}
                                        onChange={this.onChange}>
                                        <option value="">Select Status</option>
                                        <option value="TODO">TO DO</option>
                                        <option value="INPROGRESS">IN PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>
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

UpdateProjectTask.propTypes = {
    updateProjectTask: PropTypes.func.isRequired,
    errorObject: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    projectTask: state.backlog.projectTask,
    errorObject: state.errorObject
});

export default connect(
        mapStateToProps,
        { updateProjectTask, getProjectTask })
(UpdateProjectTask);