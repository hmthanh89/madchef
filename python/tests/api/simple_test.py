from core.api import http, utilities
import pytest

target = http.target("https://reqres.in")


@pytest.mark.api
def test_200():
    resp = target.get("/api/users?page=2")
    assert resp.status_code == 200


@pytest.mark.api
def test_404():
    resp = target.get("/api/users/23")
    assert resp.is_4xx()


@pytest.mark.api
def test_text_in_body():
    resp = target.get("/api/users/2")
    assert resp.text_in_body('janet.weaver@reqres.in')
    assert resp.json()['data']['last_name'] == 'Weaver'


@pytest.mark.api
def test_flow():
    auth = 'Basic %s' % utilities.base64_encode('ui:uiman')
    client = http.target('https://web.demo.reportportal.io')
    resp = client.post('/uat/sso/oauth/token',
                       params={'grant_type': 'password', 'password': '1q2w3e', 'username': 'default'},
                       headers={'Authorization': auth})
    assert resp.is_ok()
    access_token = resp.json()['access_token']
    resp = client.get('/api/v1/user/default/projects', headers={'Authorization': 'bearer %s' % access_token})
    assert resp.is_ok()
    assert resp.text_in_body('PROJECT_MANAGER')
