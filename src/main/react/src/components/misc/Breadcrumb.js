import React from 'react';
import {useSelector} from 'react-redux';
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import {NavLink} from 'react-router-dom';
import {useTranslation} from 'react-i18next';

const Breadcrumb = () => {
  const breadcrumbs = useSelector((state) => state.breadcrumb);
  const { t } = useTranslation('breadcrumbs');

  return (
    <div className="breadcrumb-wrapper">
      <Breadcrumbs separator="›" style={{ display: 'flex' }}>
        {breadcrumbs.map((crumb) => (<NavLink key={crumb.content} to={crumb.to}>{t(crumb.content)}</NavLink>))}
      </Breadcrumbs>
    </div>
  );
};

export default Breadcrumb;
