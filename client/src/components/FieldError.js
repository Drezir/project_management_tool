import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {getErrors} from '../utils/errorUtil';


export default class FieldError extends Component {
    static propTypes = {
        errors: PropTypes.object.isRequired
    }

    render() {
        return (
            <React.Fragment>
                {getErrors(this.props.errors).map(error => <span>{error}<br></br></span>)}
            </React.Fragment>
        )
    }
}
