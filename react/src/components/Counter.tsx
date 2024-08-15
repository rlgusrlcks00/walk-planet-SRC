import React, { useState, useEffect, memo } from 'react';

interface CounterProps {
  value: number;
  duration?: number;
  float?: boolean;
  animate?: boolean;
}

const Counter: React.FC<CounterProps> = ({
  value,
  duration = 2000,
  float = false,
  animate = true,
}) => {
  const [count, setCount] = useState(0);

  useEffect(() => {
    if (animate) {
      let start = 0;
      const end = value;
      const increment = end / (duration / 10);

      const timer = setInterval(() => {
        start += increment;
        if (start >= end) {
          start = end;
          clearInterval(timer);
        }
        if (float) {
          setCount(parseFloat(start.toFixed(2)));
        } else {
          setCount(Math.floor(start));
        }
      }, 10);

      return () => clearInterval(timer);
    } else {
      if (float) {
        setCount(parseFloat(value.toFixed(2)));
      } else {
        setCount(Math.floor(value));
      }
    }
  }, [value, duration, animate]);

  return <span>{count}</span>;
};

export default memo(Counter);
