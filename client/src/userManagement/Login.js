import React, { Component } from 'react';
import {login} from '../actions/securityActions';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import classnames from 'classnames';

class Login extends Component {


    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",

            errors: {}
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }
    onSubmit(e) {
        e.preventDefault(); // prevent form from reloading
        const loginRequest = {
            username: this.state.username,
            password: this.state.password
        };
        this.props.login(loginRequest);
    }
    componentWillReceiveProps(nextProps) {
        this.setState({errors: nextProps.errors});
    }

    render() {

        const {errors} = this.state;
        const errorItemsUsername = errors.errorItems 
                                    && errors.errorItems 
                                    && errors.errorItems.userDoesNotExist 
                                    && errors.errorItems.userDoesNotExist.username;

        return (
            <div className="login">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Log In</h1>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input 
                                    type="email" 
                                    className={classnames({
                                        "form-control form-control-lg": true,
                                        "is-invalid": errors.username || errorItemsUsername,
                                    })}
                                    value={this.state.username}
                                    onChange={this.onChange}
                                    placeholder="Email Address" 
                                    name="username" />
                                    {errors.username && (
                                        <div className="invalid-feedback">{errors.username}</div>
                                    )}
                                    {errorItemsUsername && (
                                        <div className="invalid-feedback">{errors.errorItems.userDoesNotExist.username.message}</div>
                                    )}

                                </div>
                                <div className="form-group">
                                    <input 
                                    type="password" 
                                    className={classnames({
                                        "form-control form-control-lg": true,
                                        "is-invalid": errors.password
                                    })}
                                    value={this.state.password}
                                    onChange={this.onChange}
                                    placeholder="Password" 
                                    name="password" />
                                    {errors.password && (
                                        <div className="invalid-feedback">{errors.password}</div>
                                    )}
                                </div>
                                <input type="submit" className="btn btn-info btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}


Login.propTypes = {
    login: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    errors: state.errors
});

export default connect(
    mapStateToProps, 
    {login}
) (Login);
