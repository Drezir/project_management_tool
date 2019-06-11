import React, { Component } from 'react';
import {formatError} from '../utils/errorUtil';

export default class ErrorPage extends Component {
    render() {
        const { errorItems, stacktrace } = this.props.errorObject;
        return (
            <div className="container">
            <p className="display-4">Errors:</p>
                <ul>
                    {
                        Object.keys(errorItems).map(function (key) {
                            const errors = errorItems[key];
                            return (
                                <li>
                                    {key}
                                    <ul>
                                        {errors.map(error => {
                                            return (
                                                <li>{error.message}: {error.value}</li>
                                            )
                                        })}
                                    </ul>
                                </li>
                            );
                        })
                    }
                </ul>
                <div className="mt-4">
                    <p className="display-4">Stacktrace:</p>
                    <textarea readOnly={true} className="w-100 stacktrace" >
                        {stacktrace}
                    </textarea>
                </div>
            </div>
        )
    }
}