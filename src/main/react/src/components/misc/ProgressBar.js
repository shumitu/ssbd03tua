import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';

const BorderLinearProgress = withStyles({
  root: {
    height: '1rem',
    backgroundColor: 'rgba(193, 29, 98, 0.9)',
  },
  bar: {
    borderRadius: '2rem',
    backgroundColor: 'rgba(254, 134, 79, 0.9)',
  },
})(LinearProgress);

const ProgressBar = () => (
  <div className="full-width">
    <BorderLinearProgress />
  </div>
);

export default ProgressBar;
