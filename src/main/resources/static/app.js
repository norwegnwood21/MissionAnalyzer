const uploadForm = document.getElementById("uploadForm");
const missionFile = document.getElementById("missionFile");
const uploadResult = document.getElementById("uploadResult");

const loadMissionsBtn = document.getElementById("loadMissionsBtn");
const missionsList = document.getElementById("missionsList");

const detailsMissionId = document.getElementById("detailsMissionId");
const loadDetailsBtn = document.getElementById("loadDetailsBtn");
const detailsResult = document.getElementById("detailsResult");

const reportMissionId = document.getElementById("reportMissionId");
const reportType = document.getElementById("reportType");
const loadReportBtn = document.getElementById("loadReportBtn");
const reportResult = document.getElementById("reportResult");

const deleteMissionId = document.getElementById("deleteMissionId");
const deleteMissionBtn = document.getElementById("deleteMissionBtn");
const deleteResult = document.getElementById("deleteResult");

function formatJson(data) {
    return JSON.stringify(data, null, 2);
}

function formatError(data) {
    if (!data) return "Неизвестная ошибка";
    if (typeof data === "string") return data;
    if (data.message) return data.message;
    return JSON.stringify(data, null, 2);
}

async function safeJson(response) {
    const text = await response.text();
    if (!text) return null;

    const contentType = response.headers.get("content-type") || "";
    if (contentType.includes("application/json")) {
        return JSON.parse(text);
    }
    return text;
}

uploadForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    if (!missionFile.files.length) {
        uploadResult.textContent = "Выберите файл";
        return;
    }

    const formData = new FormData();
    formData.append("file", missionFile.files[0]);

    try {
        const response = await fetch("/api/missions/upload", {
            method: "POST",
            body: formData
        });

        const data = await safeJson(response);

        if (!response.ok) {
            uploadResult.textContent = formatError(data);
            return;
        }

        uploadResult.textContent = formatJson(data);
        loadMissions();
    } catch (error) {
        uploadResult.textContent = "Ошибка загрузки файла";
    }
});

async function loadMissions() {
    try {
        const response = await fetch("/api/missions");
        const data = await safeJson(response);

        if (!response.ok) {
            missionsList.textContent = formatError(data);
            return;
        }

        missionsList.innerHTML = "";

        if (!Array.isArray(data) || data.length === 0) {
            missionsList.textContent = "Архив пуст";
            return;
        }

        data.forEach(mission => {
            const item = document.createElement("div");
            item.className = "mission-item";
            item.innerHTML = `
                <strong>${mission.missionId}</strong><br>
                Дата: ${mission.date ?? "-"}<br>
                Локация: ${mission.location ?? "-"}<br>
                Результат: ${mission.outcome ?? "-"}
            `;

            item.addEventListener("click", () => {
                detailsMissionId.value = mission.missionId;
                reportMissionId.value = mission.missionId;
                deleteMissionId.value = mission.missionId;
                loadDetails();
            });

            missionsList.appendChild(item);
        });
    } catch (error) {
        missionsList.textContent = "Ошибка получения списка миссий";
    }
}

async function loadDetails() {
    const missionId = detailsMissionId.value.trim();
    if (!missionId) {
        detailsResult.textContent = "Введите missionId";
        return;
    }

    try {
        const response = await fetch(`/api/missions/${encodeURIComponent(missionId)}`);
        const data = await safeJson(response);

        if (!response.ok) {
            detailsResult.textContent = formatError(data);
            return;
        }detailsResult.textContent = formatJson(data);
             } catch (error) {
                 detailsResult.textContent = "Ошибка получения деталей миссии";
             }
         }

         async function loadReport() {
             const missionId = reportMissionId.value.trim();
             const type = reportType.value;

             if (!missionId) {
                 reportResult.textContent = "Введите missionId";
                 return;
             }

             try {
                 const response = await fetch(`/api/missions/${encodeURIComponent(missionId)}/report?type=${encodeURIComponent(type)}`);
                 const data = await safeJson(response);

                 if (!response.ok) {
                     reportResult.textContent = formatError(data);
                     return;
                 }

                 reportResult.textContent = typeof data === "string" ? data : formatJson(data);
             } catch (error) {
                 reportResult.textContent = "Ошибка получения отчёта";
             }
         }

         async function deleteMission() {
             const missionId = deleteMissionId.value.trim();

             if (!missionId) {
                 deleteResult.textContent = "Введите missionId";
                 return;
             }

             const confirmed = confirm(`Удалить миссию ${missionId}?`);
             if (!confirmed) return;

             try {
                 const response = await fetch(`/api/missions/${encodeURIComponent(missionId)}`, {
                     method: "DELETE"
                 });

                 const data = await safeJson(response);

                 if (!response.ok) {
                     deleteResult.textContent = formatError(data);
                     return;
                 }

                 deleteResult.textContent = typeof data === "string" ? data : formatJson(data);
                 detailsResult.textContent = "";
                 reportResult.textContent = "";
                 loadMissions();
             } catch (error) {
                 deleteResult.textContent = "Ошибка удаления миссии";
             }
         }

         loadMissionsBtn.addEventListener("click", loadMissions);
         loadDetailsBtn.addEventListener("click", loadDetails);
         loadReportBtn.addEventListener("click", loadReport);
         deleteMissionBtn.addEventListener("click", deleteMission);

         loadMissions();