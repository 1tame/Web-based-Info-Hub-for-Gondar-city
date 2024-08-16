document.addEventListener('DOMContentLoaded', function() {
    const adminNav = document.getElementById('adminNav');

    const adminLinks = [
        { name: 'Dashboard', url: '/admin/dashboard' },
        { name: 'Manage Users', url: '/admin/manage-users' },
        { name: 'Settings', url: '/admin/settings' },
        { name: 'Insert Event Festival Information', url: '/insertEventFestivalInfo' },
        { name: 'View Event Festival Information', url: '/viewEventFestivalInfo' },
        { name: 'Insert Health Sector Information', url: '/insertHealthSectorInfo' },
        { name: 'View Health Sector Information', url: '/viewHealthSectorInfo' },
        { name: 'Report an Issue', url: '/reportIssue' },
    ];

    adminLinks.forEach(link => {
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = link.url;
        a.textContent = link.name;
        li.appendChild(a);
        adminNav.insertBefore(li, adminNav.firstChild);
    });
});
