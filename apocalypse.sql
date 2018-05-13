-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Sam 12 Mai 2018 à 16:24
-- Version du serveur :  10.1.16-MariaDB
-- Version de PHP :  7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `apocalypse`
--

-- --------------------------------------------------------

--
-- Structure de la table `activite_externe`
--

CREATE TABLE `activite_externe` (
  `id_activite_externe` int(10) NOT NULL,
  `id_type_activite` int(10) NOT NULL,
  `date_creation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `titre` varchar(70) NOT NULL,
  `description` varchar(2500) NOT NULL,
  `ville` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `activite_externe`
--

INSERT INTO `activite_externe` (`id_activite_externe`, `id_type_activite`, `date_creation`, `titre`, `description`, `ville`) VALUES
(1, 1, '2018-03-05 15:49:18', 'Restaurant', 'Description du restaurant', 'Pessac'),
(2, 2, '2018-03-05 15:49:18', 'Musée', 'Description du musée', 'Cergy'),
(3, 3, '2018-03-05 15:49:18', 'Parc d''attractions', 'Description du parc d''attraction', 'Bergerac'),
(4, 4, '2018-03-05 15:49:18', 'Zoo', 'Description du zoo.', 'Blanquefort'),
(5, 5, '2018-03-05 15:49:18', 'Concert', 'Description du concert', 'Dax');

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE `annonce` (
  `id_annonce` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `titre` varchar(50) NOT NULL,
  `description` varchar(2500) NOT NULL,
  `capacite_max` int(10) NOT NULL,
  `date_creation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modification` datetime DEFAULT NULL,
  `prix_nuit` double NOT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `annonce`
--

INSERT INTO `annonce` (`id_annonce`, `id_utilisateur`, `titre`, `description`, `capacite_max`, `date_creation`, `date_modification`, `prix_nuit`, `actif`) VALUES
(1, 2, 'Chambre dtel', 'La chambre del est spacieuse. Elle donne vue sur les cocotiers. ', 4, '2018-03-05 15:38:48', NULL, 45, 1),
(2, 3, 'Suite royale', 'Située au dernier étage de l''hôtel, notre suite Royale Mandarin offre de superbes vues panoramiques sur la ville. La décoration de cette suite duplex rappelle l''opulence des années 1930, avec sa palette de tons or, blanc, beige et prune d''une grande élégance. Chaque meuble et objet d''art a été spécialement commandé, tout comme l''escalier en métal guilloché. La tête de lit brodée reproduit une œuvre de Man Ray, un artiste de légende, pour un effet des plus saisissants. La suite est dotée d''une spacieuse salle de séjour, d''une salle à manger, d''une cuisine, d''un bar, d''un bureau et d''une salle de gym privée, ainsi que d''une chambre principale aux dimensions généreuses. L''élégante salle de bains attenante, ornée de cabochons dorés au sol, comprend un hammam et une gigantesque baignoire offrant une magnifique vue sur la ville, dont vous pouvez profiter également depuis la terrasse. De plus, si vous souhaitez bénéficier d''une deuxième chambre, vous pouvez réserver la Suite Panoramique adjacente, qui offre un splendide intérieur Art déco et comprend un espace salon-salle à manger, une chambre, une salle de bain et une terrasse extérieure privée.', 2, '2018-03-05 15:44:30', NULL, 200, 1),
(3, 2, 'll', 'szzs', 3, '2018-03-13 21:05:40', NULL, 33.12, 1),
(4, 2, 'Hey', 'Chambre', 4, '2018-03-13 21:18:09', NULL, 34.56, 0);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE `commentaire` (
  `id_commentaire` int(10) NOT NULL,
  `id_reservation` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `date_creation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `commentaire` varchar(2500) NOT NULL,
  `id_etat_commentaire` int(10) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `etat_commentaire`
--

CREATE TABLE `etat_commentaire` (
  `id_etat_commentaire` int(10) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `etat_commentaire`
--

INSERT INTO `etat_commentaire` (`id_etat_commentaire`, `libelle`) VALUES
(1, 'Validé'),
(2, 'Refusé'),
(3, 'Signalé');

-- --------------------------------------------------------

--
-- Structure de la table `etat_reservation`
--

CREATE TABLE `etat_reservation` (
  `id_etat_reservation` int(10) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `etat_reservation`
--

INSERT INTO `etat_reservation` (`id_etat_reservation`, `libelle`) VALUES
(1, 'En attente de la confirmation de l''hôtelier'),
(2, 'En attente de la confirmation du voyageur'),
(3, 'Validée'),
(4, 'Refusée');

-- --------------------------------------------------------

--
-- Structure de la table `hotel`
--

CREATE TABLE `hotel` (
  `id_hotel` int(10) NOT NULL,
  `nom_hotel` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `hotel`
--

INSERT INTO `hotel` (`id_hotel`, `nom_hotel`) VALUES
(1, 'El Plaza');

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `id_paiement` int(10) NOT NULL,
  `id_reservation` int(10) NOT NULL,
  `date_paiement` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `proposition_modification_annonce`
--

CREATE TABLE `proposition_modification_annonce` (
  `id_proposition_modif_annonce` int(10) NOT NULL,
  `id_annonce` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `modification` varchar(1000) NOT NULL,
  `date_proposition` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `id_reservation` int(10) NOT NULL,
  `id_annonce` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `date_debut_sejour` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_fin_sejour` datetime NOT NULL,
  `duree_sejour` int(10) NOT NULL,
  `prix` double NOT NULL,
  `etat_reservation` tinyint(1) NOT NULL DEFAULT '1',
  `id_statut_reservation` int(10) NOT NULL DEFAULT '1',
  `id_etat_reservation` int(10) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `reservation`
--

INSERT INTO `reservation` (`id_reservation`, `id_annonce`, `id_utilisateur`, `date_debut_sejour`, `date_fin_sejour`, `duree_sejour`, `prix`, `etat_reservation`, `id_statut_reservation`, `id_etat_reservation`) VALUES
(1, 1, 3, '2018-03-05 15:40:10', '2018-03-11 15:40:00', 6, 270, 1, 3, 3),
(2, 1, 3, '2018-03-05 15:40:10', '2018-03-12 15:40:00', 7, 270, 1, 4, 4),
(3, 2, 2, '2018-05-07 22:33:30', '2018-05-16 00:00:00', 5, 25, 1, 1, 1),
(5, 1, 3, '2021-03-05 00:00:00', '2021-06-05 00:00:00', 3, 135, 1, 1, 1),
(6, 1, 3, '2019-03-05 00:00:00', '2019-03-05 00:00:00', 1, 45, 1, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `statut_reservation`
--

CREATE TABLE `statut_reservation` (
  `id_statut_reservation` int(10) NOT NULL,
  `libelle` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `statut_reservation`
--

INSERT INTO `statut_reservation` (`id_statut_reservation`, `libelle`) VALUES
(1, 'En attente'),
(2, 'A venir'),
(3, 'En cours'),
(4, 'Terminée');

-- --------------------------------------------------------

--
-- Structure de la table `type_activite`
--

CREATE TABLE `type_activite` (
  `id_type_activite` int(10) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `type_activite`
--

INSERT INTO `type_activite` (`id_type_activite`, `libelle`) VALUES
(1, 'Restauration'),
(2, 'Musées'),
(3, 'Parc d''attractions'),
(4, 'Nature et parcs'),
(5, 'Evènement');

-- --------------------------------------------------------

--
-- Structure de la table `type_utilisateur`
--

CREATE TABLE `type_utilisateur` (
  `id_type_utilisateur` int(10) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `type_utilisateur`
--

INSERT INTO `type_utilisateur` (`id_type_utilisateur`, `libelle`) VALUES
(1, 'Administrateur'),
(2, 'Hotelier'),
(3, 'Standard');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int(10) NOT NULL,
  `id_type_utilisateur` int(10) NOT NULL DEFAULT '3',
  `id_hotel` int(10) DEFAULT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `mail` varchar(70) NOT NULL,
  `mot_de_passe` varchar(25) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `adresse` varchar(70) NOT NULL,
  `code_postal` varchar(10) NOT NULL,
  `ville` varchar(50) NOT NULL,
  `point_bonus` int(100) DEFAULT '0',
  `actif` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `id_type_utilisateur`, `id_hotel`, `nom`, `prenom`, `mail`, `mot_de_passe`, `mobile`, `adresse`, `code_postal`, `ville`, `point_bonus`, `actif`) VALUES
(1, 1, NULL, 'LIM', 'Sindy', 'sindy.lim91@gmail.com', 'test01', '0678653401', '100 rue des tests', '75015', 'Paris', 0, 1),
(2, 2, 1, 'HAKANJIN', 'Roméo', 'hakanjin.romeo96@gmail.com', 'test02', '0697448295', '3 rue des curés', '95800', 'Cergy', 0, 1),
(3, 3, NULL, 'DIAGNE', 'Massamba', 'massdinho10@gmail.com', 'test03', '0734546501', '3 avenue du test', '93600', 'Aulnay-sous-bois', 0, 1),
(4, 3, NULL, 'testos', 'rockos', 'test', 'test', '015161', '6 rue du test', 'test05', 'Testos', 50, 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `activite_externe`
--
ALTER TABLE `activite_externe`
  ADD PRIMARY KEY (`id_activite_externe`),
  ADD KEY `id_type_activite` (`id_type_activite`);

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`id_annonce`),
  ADD KEY `id_utilisateur` (`id_utilisateur`);

--
-- Index pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`id_commentaire`),
  ADD KEY `id_reservation` (`id_reservation`),
  ADD KEY `id_utilisateur` (`id_utilisateur`),
  ADD KEY `id_etat_commentaire` (`id_etat_commentaire`);

--
-- Index pour la table `etat_commentaire`
--
ALTER TABLE `etat_commentaire`
  ADD PRIMARY KEY (`id_etat_commentaire`);

--
-- Index pour la table `etat_reservation`
--
ALTER TABLE `etat_reservation`
  ADD PRIMARY KEY (`id_etat_reservation`);

--
-- Index pour la table `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id_hotel`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`id_paiement`),
  ADD KEY `id_reservation` (`id_reservation`);

--
-- Index pour la table `proposition_modification_annonce`
--
ALTER TABLE `proposition_modification_annonce`
  ADD PRIMARY KEY (`id_proposition_modif_annonce`),
  ADD KEY `id_annonce` (`id_annonce`),
  ADD KEY `id_utilisateur` (`id_utilisateur`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_reservation`),
  ADD KEY `id_annonce` (`id_annonce`),
  ADD KEY `id_utilisateur` (`id_utilisateur`),
  ADD KEY `id_statut_reservation` (`id_statut_reservation`),
  ADD KEY `id_etat_reservation` (`id_etat_reservation`);

--
-- Index pour la table `statut_reservation`
--
ALTER TABLE `statut_reservation`
  ADD PRIMARY KEY (`id_statut_reservation`);

--
-- Index pour la table `type_activite`
--
ALTER TABLE `type_activite`
  ADD PRIMARY KEY (`id_type_activite`);

--
-- Index pour la table `type_utilisateur`
--
ALTER TABLE `type_utilisateur`
  ADD PRIMARY KEY (`id_type_utilisateur`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD KEY `id_type_utilisateur` (`id_type_utilisateur`),
  ADD KEY `id_hotel` (`id_hotel`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `activite_externe`
--
ALTER TABLE `activite_externe`
  MODIFY `id_activite_externe` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `id_annonce` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `commentaire`
--
ALTER TABLE `commentaire`
  MODIFY `id_commentaire` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `etat_commentaire`
--
ALTER TABLE `etat_commentaire`
  MODIFY `id_etat_commentaire` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `etat_reservation`
--
ALTER TABLE `etat_reservation`
  MODIFY `id_etat_reservation` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id_hotel` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `id_paiement` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `proposition_modification_annonce`
--
ALTER TABLE `proposition_modification_annonce`
  MODIFY `id_proposition_modif_annonce` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id_reservation` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `statut_reservation`
--
ALTER TABLE `statut_reservation`
  MODIFY `id_statut_reservation` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `type_activite`
--
ALTER TABLE `type_activite`
  MODIFY `id_type_activite` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `type_utilisateur`
--
ALTER TABLE `type_utilisateur`
  MODIFY `id_type_utilisateur` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `activite_externe`
--
ALTER TABLE `activite_externe`
  ADD CONSTRAINT `activite_externe_ibfk_1` FOREIGN KEY (`id_type_activite`) REFERENCES `type_activite` (`id_type_activite`);

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `annonce_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `commentaire_ibfk_1` FOREIGN KEY (`id_reservation`) REFERENCES `reservation` (`id_reservation`),
  ADD CONSTRAINT `commentaire_ibfk_2` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `commentaire_ibfk_3` FOREIGN KEY (`id_etat_commentaire`) REFERENCES `etat_commentaire` (`id_etat_commentaire`);

--
-- Contraintes pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `paiement_ibfk_1` FOREIGN KEY (`id_reservation`) REFERENCES `reservation` (`id_reservation`);

--
-- Contraintes pour la table `proposition_modification_annonce`
--
ALTER TABLE `proposition_modification_annonce`
  ADD CONSTRAINT `proposition_modification_annonce_ibfk_1` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`),
  ADD CONSTRAINT `proposition_modification_annonce_ibfk_2` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `reservation_ibfk_3` FOREIGN KEY (`id_statut_reservation`) REFERENCES `statut_reservation` (`id_statut_reservation`),
  ADD CONSTRAINT `reservation_ibfk_4` FOREIGN KEY (`id_etat_reservation`) REFERENCES `etat_reservation` (`id_etat_reservation`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`id_type_utilisateur`) REFERENCES `type_utilisateur` (`id_type_utilisateur`),
  ADD CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`id_hotel`) REFERENCES `hotel` (`id_hotel`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
