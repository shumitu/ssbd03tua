import { applyMiddleware, createStore } from 'redux';
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import apiMiddleware from './middlewares/apiMiddleware';
import rootReducer from './reducers/rootReducer';
import { accessDeniedMiddleware, saveAuthToken } from './middlewares/middlewares';

const persistConfig = {
  key: 'root',
  storage,
  blacklist: ['auth', 'currentAccessLevel'],
};

const middlewares = [saveAuthToken, apiMiddleware, accessDeniedMiddleware];
const persistedReducer = persistReducer(persistConfig, rootReducer);

export default () => {
  const store = createStore(persistedReducer, applyMiddleware(...middlewares));
  const persistor = persistStore(store);
  return { store, persistor };
};
