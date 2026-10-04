import {createRoot} from 'react-dom/client'
import {HashRouter} from 'react-router-dom'
import {AuthProvider} from './auth.jsx'
import App from './App.jsx'
import Fx from './components/Fx.jsx'
import './styles.css'
createRoot(document.getElementById('root')).render(<HashRouter><AuthProvider><Fx/><App/></AuthProvider></HashRouter>)
