import React from 'react';
import {useTranslation} from 'react-i18next';
import {FilterTextField, StyledCheckbox} from './themes';

export const getDataRow = (row, keys) => {
  const newRow = {};
  keys.forEach((key, index) => {
    newRow[`col${index.toString()}`] = row[key];
  });
  return newRow;
};

export const IndeterminateCheckbox = React.forwardRef(
  ({ indeterminate, ...rest }, ref) => {
    const defaultRef = React.useRef();
    const resolvedRef = ref || defaultRef;

    React.useEffect(() => {
      resolvedRef.current.indeterminate = indeterminate;
    }, [resolvedRef, indeterminate]);

    return (
      <>
        <StyledCheckbox inputRef={resolvedRef} {...rest} />
      </>
    );
  },
);

export function DefaultColumnFilter({ column: { filterValue, preFilteredRows, setFilter } }) {
  const count = preFilteredRows.length;
  const { t } = useTranslation('table');
  return (
    <FilterTextField
      variant="outlined"
      value={filterValue || ''}
      onChange={(e) => {
        setFilter(e.target.value || undefined);
      }}
      label={t('search', { count })}
    />
  );
}
