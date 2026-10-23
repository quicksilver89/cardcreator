import Keycloak from 'https://esm.sh/keycloak-js';

const keycloak = new Keycloak(
{
    url: `${window.location.origin}/auth`,
    realm: 'cardcreator',
    clientId: 'cardcreator',
} );

let initPromise: Promise<boolean> | null = null;

export async function initAuth()
{
    if( !initPromise )
    {
        initPromise = keycloak.init(
        {
            onLoad: 'login-required',
            checkLoginIframe: false,
            pkceMethod: 'S256',
        } );
    }
    return initPromise;
}
  
export async function getAccessToken()
{
    await initAuth();
    await keycloak.updateToken( 60 );
    return keycloak.token;
}

export const logout = () => keycloak.logout( {redirectUri: window.location.origin} );

initAuth();
