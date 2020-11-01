import React from 'react';
import './lib/bootstrap/css/bootstrap.min.css';
import './resources/css/style.css';
import {Provider} from 'react-redux';
import {PersistGate} from 'redux-persist/integration/react';
import {Router} from 'react-router';
import Layout from './components/Layout';
import store from './components/redux/store';
import MainController from './components/MainController';
import HelmetWrapper from './components/Helmet';
import history from './components/misc/history';

const App = () => (
  <Provider store={store().store}>
    <PersistGate loading={null} persistor={store().persistor}>
      <HelmetWrapper />
      <Router history={history}>
        <Layout>
          <MainController />
        </Layout>
      </Router>
    </PersistGate>
  </Provider>
);

export default App;
