import React, { Component } from 'react';
import classnames from 'classnames';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { deleteProjectTask } from '../../../actions/backlogActions';
import PropTypes from 'prop-types';

class ProjectTask extends Component {

    onDeleteClick = (backlogId, projectTaskId) => {
        this.props.deleteProjectTask(backlogId, projectTaskId);
    }

    render() {

        const { projectTask } = this.props;
        const highestPriority = projectTask.priority === 1;
        const mediumPriority = projectTask.priority === 2;
        const lowPriority = projectTask.priority === 3;

        const { projectIdentifier, projectSequence } = projectTask;

        return (
            <div className="card mb-1 bg-light">

                <div
                    className={classnames({
                        "card-header": true,
                        "bg-danger": highestPriority,
                        "bg-warning text-secondary": mediumPriority,
                        "bg-info": lowPriority
                    })}>
                    ID: {projectTask.projectSequence} -- Priority: {projectTask.priority}
                </div>
                <div className="card-body bg-light">
                    <h5 className="card-title">{projectTask.summary}</h5>
                    <p className="card-text text-truncate ">
                        {projectTask.acceptanceCriteria}
                    </p>
                    <Link to={`/updateProjectTask/${projectIdentifier}/${projectSequence}`} className="btn btn-primary">
                        View / Update
                    </Link>

                    <button onClick={this.onDeleteClick.bind(this, projectIdentifier, projectSequence)} className="btn btn-danger ml-4">
                        Delete
                    </button>
                </div>
            </div>
        )
    }
}

ProjectTask.propTypes = {
    deleteProjectTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps,
    {deleteProjectTask})
(ProjectTask);