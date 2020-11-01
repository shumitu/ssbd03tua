import {spring} from 'react-router-transition';

function bounce(val) {
  return spring(val, {
    stiffness: 330,
    damping: 22,
  });
}

export function mapStyles(styles) {
  return {
    opacity: styles.opacity,
    transform: `scale(${styles.scale})`,
    width: '100%',
  };
}

export const bounceTransition = {
  atEnter: {
    opacity: 0,
    scale: 0.8,
  },
  atLeave: {
    opacity: bounce(0),
    scale: bounce(1),
  },
  atActive: {
    opacity: bounce(1),
    scale: bounce(1),
  },
};
