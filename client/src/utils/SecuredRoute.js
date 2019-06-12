import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {Route, Redirect} from 'react-router-dom';
import {connect} from 'react-redux';

const SecuredRoute = ({component: Component, security, ...otherProps}) => (
    <Route 
    {...otherProps} 
    render={props => security.validToken === true ? 
        (<Component {...props}/>) 
        : 
        (<Redirect to={"/login"}/>)}
    />
);

SecuredRoute.propTypes = {
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security
});

export default connect(
    mapStateToProps
)(SecuredRoute);