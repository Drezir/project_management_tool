import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import Backlog from './Backlog';
import { connect } from 'react-redux';
import { getBacklog } from '../../actions/backlogActions';
import ErrorPage from '../ErrorPage';

class ProjectBoard extends Component {

    constructor(props) {
        super(props);
        this.state = {
            errorObject: {
                errors: {}
            }
        }
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        this.props.getBacklog(id);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errorObject) {
            this.setState({
                errorObject: nextProps.errorObject
            });
        }
    }

    render() {
        const { id } = this.props.match.params;
        const { projectTasks } = this.props.backlog;
        const errorObject = this.state.errorObject;
        const boardAlgorithm = (errorObject, projectTasks) => {
            if (errorObject.stacktrace) {
                return <ErrorPage errors={errorObject} />;
            } else {
                return <Backlog projectTasks={projectTasks} />
            }
        };

        let boardContent = boardAlgorithm(errorObject, projectTasks);

        return (
            <div className="container">
                <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
                    <i className="fas fa-plus-circle"> Create Project Task</i>
                </Link>
                <br />
                <hr />
                {boardContent}
            </div>)
    }
}

ProjectBoard.propTypes = {
    getBacklog: PropTypes.func.isRequired,
    backlog: PropTypes.object.isRequired,
    errorObject: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    backlog: state.backlog,
    errorObject: state.errorObject
});

export default connect(
    mapStateToProps,
    { getBacklog })
    (ProjectBoard);